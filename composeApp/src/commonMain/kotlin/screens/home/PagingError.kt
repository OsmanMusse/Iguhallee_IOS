package screens.home

enum class PagingError(val errorMsg: String) {
    INTERNET_CONNECTION("INTERNET IS NOT CONNECTED"),
    QUERY_NOT_FOUND_FROM_DATABASE("NOT ITEMS FOUND")
}