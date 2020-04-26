fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
### lint
```
fastlane lint
```
Runs lint on all variants of the sdk and app
### test
```
fastlane test
```
Runs all the jvm tests in the sdk
### release
```
fastlane release
```
Assembles all build variants and deploy artifacts
### firebase_app_dist
```
fastlane firebase_app_dist
```
Upload build to Firebase;
required parameters are flavour, build_type, tester_groups;
optional parameters is git_branch

----

## Android
### android run_workflow
```
fastlane android run_workflow
```
This lane will run workflow depending on the branch

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
