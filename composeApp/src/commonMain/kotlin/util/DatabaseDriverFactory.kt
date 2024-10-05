package util

import app.cash.sqldelight.db.SqlDriver
import com.ramaas.iguhallee.Database

expect class DatabaseDriverFactory() {
    fun create(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory): Database {
    val driver = driverFactory.create()
    return Database(driver)
}