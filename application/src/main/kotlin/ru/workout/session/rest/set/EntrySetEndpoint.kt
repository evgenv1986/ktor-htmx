package ru.workout.session.rest.set

import io.ktor.server.application.ApplicationCall

class EntrySetEndpoint(
    call: ApplicationCall,
    val form: EntrySetForm = EntrySetForm(call)
) {
    suspend fun handle(call: ApplicationCall) {
        val taskId = call.parameters["taskId"]
//        val requestParams = call.receiveParameters()

        form.view(taskId)
    }

}