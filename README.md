# Arad Push Notification system (APN)

**APN** documentation for android applications. It can be use in **Kotlin**

## Installation

Always get the latest version with the following code

## How to use

### Import

# Step 1:
Add it in your root build.gradle at the end of repositories:
``` groovy
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
# Step 2:
Add the dependency:
```groovy
implementation 'com.github.araditc:Arad.Push.Android.SDK:1.0.4'
```