# Doctor List

## Project Setup
* MVVM + Clean architecture
* Dagger (Component, SubComponent with scoping)
* Multi modules
* Unit Tests with MockK
* buildSrc
* RxJava
* Navigation Component

## Issues faced
I opted for paging library which turns out wrong dececision. While I started implementing recent
doctors + sorting doctors I notice the PagedList is not flexible for modefication when ever you try
to add or remvoe it crash with exception.

If you are intressted on the paging library implementation you can checkout branch `with-paging-library`

## If I had more time
* Invest little time to find how I can implement paging library with local sorting
* Travis for lint, building and Testing

## Demo
* Release : Test [me](demo/app-prod-release.apk)

<img src="demo/screenshot1.png" width="400" />
<br/>
<img src="demo/screenshot2.png" width="400" />
<br/>
<img src="demo/screenshot3.png" height="400" />
<br/>
<img src="demo/screenshot4.png" height="400" />

## How to build ?
1- add this to your `local.properties`
```properties
devEndpoint=http://DOMAIN_NAME_OR_IP/
prodEndpoint=http://DOMAIN_NAME_OR_IP/
//For release
storePassword=123456789
keyAlias=DoctorList
keyPassword=987654321
```

2- run `./gradlew build`