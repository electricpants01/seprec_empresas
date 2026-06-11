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

### versionName (automatic)
Derived from the branch/tag name. Examples:
- `release/2026.06.11` → versionName `2026.06.11`
- `v2026.06.11` → versionName `2026.06.11`
- Local: from latest git tag, falls back to `0.0.0`

### versionCode (manual)
Controlled by the developer. Set via:
- `gradle.properties`: `appVersionCodeOverride=X`
- CI: `VERSION_CODE` GitHub Secret
- Defaults to `1` if not set

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
**Trigger**: Pushing to `release/*` branch or `v*` tag

| Step | Action |
|---|---|
| Checkout | `actions/checkout@v4` (fetch-depth: 0) |
| Get version | Extracts version from branch/tag name, sets `VERSION_NAME` env var |
| Decode keystore | `base64 --decode` from `KEYSTORE_BASE64` secret |
| JDK 21 | `actions/setup-java@v4` |
| Android SDK | `android-actions/setup-android@v3` |
| Build signed APK | `./gradlew assembleRelease` with `VERSION_CODE` secret |
| GitHub Release | `softprops/action-gh-release@v2` named `release-YYYY.MM.DD` |

## Required GitHub Secrets

| Secret | Purpose |
|---|---|
| `KEYSTORE_BASE64` | Base64-encoded JKS keystore file |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias name |
| `KEY_PASSWORD` | Key password (may be same as keystore password) |
| `VERSION_CODE` | Manual version code (integer, e.g., `1`, `2`, `3`) |

## How to Release

```bash
# Encode the keystore for GitHub Secret (do this once)
base64 -i bolivia_empresas.jks | pbcopy

# Create and push a release branch
# Release will be named: release-2026.06.11
# App versionName: 2026.06.11
# App versionCode: from VERSION_CODE secret (set manually in GitHub)
git checkout -b release/2026.06.11
git push origin release/2026.06.11
```

The Release workflow builds a signed APK and publishes it to GitHub Releases automatically.

## Build Environment

| Tool | Version |
|---|---|
| OS | ubuntu-latest |
| JDK | Temurin 21 |
| Android SDK | platform 36, build-tools 36.0.0 |
| AGP | 9.1.0 |
| Kotlin | 2.2.10 |
| Gradle | 9.3.1 (via wrapper) |
