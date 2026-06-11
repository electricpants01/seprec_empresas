# Skill: Android CI/CD with GitHub Actions

## Description
Continuous Integration and Delivery for the Seprec Bolivia / Bolivia Empresas Android app using GitHub Actions. Builds debug APKs on every push/PR and creates signed GitHub Releases when pushing to release branches or version tags.

## Local Setup

Set `JAVA_HOME` to Android Studio's bundled JDK 21 (Gradle 9.1+ requires JVM 17+):

```bash
echo 'export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"' >> ~/.zshrc
source ~/.zshrc
```

No `org.gradle.java.home` needed in `gradle.properties`.

## Versioning

Both `versionName` and `versionCode` are manually updated in `app/build.gradle.kts`:

```kotlin
versionCode = 1
versionName = "2026.06.11"
```

## Signing Config

The `app/build.gradle.kts` reads signing credentials from environment variables. If unavailable (local builds or CI build workflow), it falls back to the debug keystore.

```kotlin
val myKeystorePassword: String? = System.getenv("KEYSTORE_PASSWORD")
val myKeyAlias: String? = System.getenv("KEY_ALIAS")
val myKeyPassword: String? = System.getenv("KEY_PASSWORD") ?: myKeystorePassword
val myKeystoreFile = rootProject.file("bolivia_empresas.jks")
val canSign = myKeystoreFile.exists() && !myKeystorePassword.isNullOrBlank() && !myKeyAlias.isNullOrBlank()
```

With credentials → uses the real keystore. Without → falls back to debug.

## Workflows

### 1. Build (`build.yml`)
**Trigger**: Push or PR to `main`/`master`

| Step | Action |
|---|---|
| Checkout | `actions/checkout@v4` |
| JDK 21 | `actions/setup-java@v4` (Temurin 21) |
| Android SDK | `android-actions/setup-android@v3` (platform 36, build-tools 36) |
| Build | `./gradlew assembleDebug` |
| Test | `./gradlew testDebugUnitTest` |
| Artifact | Uploads debug APK |

### 2. Release (`release.yml`)
**Trigger**: Pushing to `release/*` branch or `v*` tag
**Environment**: `PROD`

| Step | Action |
|---|---|
| Checkout | `actions/checkout@v4` |
| Get version | Extracts version from branch/tag name |
| Decode keystore | `base64 --decode` from `KEYSTORE_BASE64` secret → `bolivia_empresas.jks` |
| JDK 21 | `actions/setup-java@v4` |
| Android SDK | `android-actions/setup-android@v3` |
| Build signed APK | `./gradlew assembleRelease` with PROD secrets |
| GitHub Release | `softprops/action-gh-release@v2` named `release-YYYY.MM.DD` |

## GitHub Environment: PROD

All secrets are stored in the `PROD` environment. The release workflow references `environment: PROD`.

### Required Secrets

| Secret | Purpose |
|---|---|
| `KEYSTORE_BASE64` | Base64-encoded JKS keystore file |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias name |
| `KEY_PASSWORD` | Key password (may be same as keystore password) |

## How to Release

```bash
# Encode the keystore for GitHub Secret (do this once)
base64 -i bolivia_empresas.jks | tr -d '\n' | pbcopy
# Paste into Settings → Environments → PROD → Secrets → KEYSTORE_BASE64

# Remove keystore from git (do this once)
echo "bolivia_empresas.jks" >> .gitignore
git rm --cached bolivia_empresas.jks
git commit -m "Move keystore to GitHub Secrets"

# Bump version manually in app/build.gradle.kts:
#   versionCode = 2
#   versionName = "2026.06.12"

# Push a release branch
# Release will be named: release-2026.06.12
git checkout -b release/2026.06.12
git push origin release/2026.06.12
```

The Release workflow decodes the keystore from the secret, builds a signed APK, and publishes it to GitHub Releases automatically.

## Build Environment

| Tool | Version |
|---|---|
| OS | ubuntu-latest |
| JDK | Temurin 21 |
| Android SDK | platform 36, build-tools 36.0.0 |
| AGP | 9.1.0 |
| Kotlin | 2.2.10 |
| Gradle | 9.3.1 (via wrapper) |
