# kotlin-js-cloud-function-linebot

LINE Bot written in Kotlin/JS and running with Cloud Functions for Firebase.

|image|qr|
|:---:|:---:|
|![image](https://raw.githubusercontent.com/sakebook/kotlin-js-cloud-function-linebot/master/art/image.png)|![image](https://raw.githubusercontent.com/sakebook/kotlin-js-cloud-function-linebot/master/art/qr.png)|

Parrot return. Text only.

## Environment
- Kotlin v1.3.71
- Gradle v6.3
- dukat v0.0.28

## Build

```sh
$ ./gradlew clean compileKotlinJs
```

## Debug

```sh
$ cd build/js/packages/kotlin-js-cloud-function-linebot/ && npx @google-cloud/functions-framework --target=message ; cd -
```

## Deploy

You'll need a Channel Access Token to try it.

```sh
$ ./gradlew clean compileKotlinJs
$ ./gradlew cloudFunctions
$ cd functions && gcloud functions deploy message --region asia-northeast1 --trigger-http --runtime nodejs8 --update-env-vars CHANNEL_ACCESS_TOKEN="YOUR TOKEN" && cd -
```

---

## [LICENSE](https://github.com/sakebook/kotlin-js-cloud-function-linebot/blob/master/LICENSE)
Copyright (c) 2020 sakebook Licensed under the [Apache License](https://github.com/sakebook/kotlin-js-cloud-function-linebot/blob/master/LICENSE).
