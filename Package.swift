// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorTimerNotification",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorTimerNotification",
            targets: ["TimerNotificationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "TimerNotificationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/TimerNotificationPlugin"),
        .testTarget(
            name: "TimerNotificationPluginTests",
            dependencies: ["TimerNotificationPlugin"],
            path: "ios/Tests/TimerNotificationPluginTests")
    ]
)