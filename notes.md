# Получить следующий шаг тренировки 
GET /http://localhost:8080/workouts/performances/{stepId}
получение из бд следующего шага 
    TaskOfStep(
        "Подтягивания",
        12.1,
        20)
передача этого шага в форму html в ответ пользователю

ввод пользователем результатов подхода

обработка подхода
    InputExerciseStep(
        "Подтягивания",
        12.1,
        20)
    performStep = new( id = input.id, Exercise(input) )

