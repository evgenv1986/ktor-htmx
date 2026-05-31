package ru.workout.application.provider.workoutPlan

/*
выполняемый подход задания-упражнения (The current approach of the task is exercises)
результирующий набор шаблонов повторений - repetitions template result set - resulting set of repetitions
active task -
    exercise: подтягивания
    totalReps: 35
    templateSetOfReps: [10, 15, 20, 23, 25, 27, 29, 30, 31, 32, 33, 34, 35]
    weight: 0
performed task -
    exercise: подтягивания

Set Of Reps for task (
    exercise: подтягивания
    total: 35
    ):{
        if
        return setOf<Reps>(10, 15, 20, 23, 25, 27, 29, 30, 31, 32, 33, 34, 35)
    }

 */