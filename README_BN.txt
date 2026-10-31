দ্বীনিয়াত মক্তব ম্যানেজার — Android প্রজেক্ট

এই ZIP সরাসরি APK নয়। APK তৈরি করতে Android Studio ও Android SDK-সহ কম্পিউটার প্রয়োজন।

APK বানানোর নিয়ম:
1. Android Studio ইনস্টল করে এই ফোল্ডারটি Open করুন।
2. Gradle Sync শেষ হতে দিন এবং প্রয়োজন হলে Android SDK Platform 35 ইনস্টল করুন।
3. Build > Build Bundle(s) / APK(s) > Build APK(s) চাপুন।
4. APK: app/build/outputs/apk/debug/app-debug.apk

ব্যাকআপ:
- অ্যাপের Backup ট্যাবে “Google Drive / ফোনে ব্যাকআপ” চাপুন।
- Android ফাইল পিকার খুললে Google Drive নির্বাচন করে ফাইল Save করুন। Google Drive না দেখালে ফোনের Downloads-এ রাখুন এবং পরে Drive-এ আপলোড করুন।
- ফিরিয়ে আনতে “Drive / ফোন থেকে ফিরিয়ে আনুন” চাপুন এবং আগের JSON ফাইলটি নির্বাচন করুন।
- এটি ব্যবহারকারীর হাতে চালানো ব্যাকআপ/রিস্টোর। স্বয়ংক্রিয় Google Drive sync এখনো যুক্ত নয়; এর জন্য Google OAuth কনফিগারেশন ও নিরাপদ সার্ভার/সিঙ্ক ব্যবস্থা দরকার।
- ব্যাকআপ না নিয়ে অ্যাপ আনইনস্টল করবেন না।

অফলাইন:
মূল অ্যাপ স্ক্রিন অফলাইনে চলে এবং ডেটা WebView LocalStorage-এ থাকে। অ্যাপের ডেটা ক্লিয়ার বা আনইনস্টল করলে লোকাল ডেটা মুছে যেতে পারে; তাই নিয়মিত Drive-এ ব্যাকআপ রাখুন।
