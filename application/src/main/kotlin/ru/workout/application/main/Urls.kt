package ru.workout.rest

const val WORKOUTS = "/workouts"
const val WORKOUT_PLANS = "$WORKOUTS/plans"
const val WORKOUT_PLANS_NEW = "$WORKOUT_PLANS/new"
const val WORKOUTS_SESSIONS = "$WORKOUTS/sessions"
const val BEGIN_WORKOUT = "$WORKOUTS_SESSIONS/{sessionId}/beginning"
const val WORKOUT_SESSION = "$WORKOUTS_SESSIONS/{sessionId}"
const val STEPS = "/steps"
const val COMPLETE_STEP = "$STEPS/{stepId}/completion"
