package com.kurly.report.ui

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/28
 */
sealed class SendUserNotification {
    data class Toast(val message: String) : SendUserNotification()
    data class Alert(val message: String) : SendUserNotification()
}