package ru.workout.application.main.entrySet

import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import ru.workout.rest.ENTRY_SET
import ru.workout.rest.ENTRY_SET_NEW
import ru.workout.session.rest.set.EntrySetEndpoint

fun Route.entrySetConfig() {
    get(ENTRY_SET_NEW) {
        EntrySetEndpoint(call).handle(call)
    }
}