package ru.workout.rest

const val WORKOUTS = "/workouts"
const val WORKOUT_PLANS = "$WORKOUTS/plans"
const val WORKOUT_PLANS_NEW = "$WORKOUT_PLANS/new"
const val WORKOUTS_SESSIONS = "$WORKOUTS/sessions"
const val BEGIN_WORKOUT = "$WORKOUTS_SESSIONS/{sessionId}/beginning"
const val WORKOUT_SESSION = "$WORKOUTS_SESSIONS/{sessionId}"
const val STEPS = "/steps"
const val COMPLETE_STEP = "$STEPS/{stepTemplateId}/completion"

const val TASKS = "$WORKOUT_SESSION/tasks"
const val TASK = "$TASKS/{taskId}"

const val COMPLETION_REPS = "$TASK/completion-reps"
const val COMPLETION_REPS_NEW = "/completion-reps/new"
const val COMPLETION_REP = "/completion-reps"
