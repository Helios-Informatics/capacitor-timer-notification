import Foundation

@objc public class TimerNotification: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
