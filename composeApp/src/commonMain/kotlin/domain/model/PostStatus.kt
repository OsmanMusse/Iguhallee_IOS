package domain.model

enum class PostStatus(val text: String) {
    EXPIRED(text = "Expired"),
    APPROVED("Approved"),
    WAITING("Waiting for approval"),
    DECLINED("Disapproved");
}