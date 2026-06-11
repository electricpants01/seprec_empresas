# Skill: Android CI/CD with GitHub Actions

## Description
Continuous Integration and Delivery for the Seprec Bolivia / Bolivia Empresas Android app using GitHub Actions. Builds debug APKs on every push/PR and creates signed GitHub Releases when tags are pushed.

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
| Artifact | Uploads `app-debug.apk` |

### 2. Release (`release.yml`)
**Trigger**: Pushing a tag matching `v*` (e.g., `v1.0.0`)

| Step | Action |
|---|---|
| Checkout | `actions/checkout@v4` |
| Decode keystore | `base64 --decode` from `KEYSTORE_BASE64` secret → `bolivia_empresas.jks` |
| JDK 21 | `actions/setup-java@v4` |
| Android SDK | `android-actions/setup-android@v3` |
| Build signed APK | `./gradlew assembleRelease` |
| GitHub Release | `softprops/action-gh-release@v2` with APK attached |

## Required GitHub Secrets

| Secret | Purpose |
|---|---|
| `KEYSTORE_BASE64` | Base64-encoded JKS keystore file |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias name |
| `KEY_PASSWORD` | Key password (may be same as keystore password) |

## Signing Config

The `app/build.gradle.kts` reads signing credentials from environment variables:

```kotlin
val keystorePassword: String? = System.getenv("KEYSTORE_PASSWORD")
val keyAlias: String? = System.getenv("KEY_ALIAS")
val keyPassword: String? = System.getenv("KEY_PASSWORD") ?: keystorePassword
val keystoreFile = rootProject.file("bolivia_empresas.jks")
val canSign = keystoreFile.exists() && keystorePassword != null && keyAlias != null
```

If env vars are not set (local builds), the release build is unsigned. Only the CI/CD pipeline produces signed APKs.

## How to Release

```bash
# Encode the keystore for GitHub Secret (do this once)
base64 -i bolivia_empresas.jks | pbcopy

# Create and push a version tag
git tag v1.0.0
git push origin v1.0.0
```

The Release workflow automatically builds a signed APK and publishes it to GitHub Releases.

## Build Environment

| Tool | Version |
|---|---|
| OS | ubuntu-latest |
| JDK | Temurin 21 |
| Android SDK | platform 36, build-tools 36.0.0 |
| AGP | 9.1.0 |
| Kotlin | 2.2.10 |
| Gradle | 9.3.1 (via wrapper) |
