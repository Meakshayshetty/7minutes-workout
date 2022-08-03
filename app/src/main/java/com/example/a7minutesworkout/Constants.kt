package com.example.a7minutesworkout


object Constants {
    fun defaultExerciseList() :ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val stretch = ExerciseModel(
            1, "Stretch", R.drawable.stretch, false, false
        )

        exerciseList.add(stretch)

        val jumpingJack = ExerciseModel(
            2, "Jumping jack", R.drawable.jumpingjack, false, false
        )

        exerciseList.add(jumpingJack)

        val plank = ExerciseModel(
            3, "plank", R.drawable.plunk, false, false
        )

        exerciseList.add(plank)

        val exercise_bike = ExerciseModel(
            4, "exercise bike", R.drawable.exercise_bike, false, false
        )

        exerciseList.add(exercise_bike)

        val pullup = ExerciseModel(
            5, "pull up", R.drawable.pullup, false, false
        )

        exerciseList.add(pullup)

        val pushup = ExerciseModel(
            6, "push up", R.drawable.pushup, false, false
        )

        exerciseList.add(pushup)

        val seated_forwardBend = ExerciseModel(
            7, "seated forward bend ", R.drawable.seated_forwordbend, false, false
        )

        exerciseList.add(seated_forwardBend)

        val situp = ExerciseModel(
            8, "sit up", R.drawable.situp, false, false
        )

        exerciseList.add(situp)

        val skipping = ExerciseModel(
            9, "Skipping", R.drawable.skipping, false, false
        )

        exerciseList.add(skipping)

        val squat = ExerciseModel(
            10, "squat", R.drawable.squat, false, false
        )

        exerciseList.add(squat)

        val squat_tostand = ExerciseModel(
            11, "squat to stand", R.drawable.squat_tostand, false, false
        )

        exerciseList.add(squat_tostand)

        val standing_forwardBend = ExerciseModel(
            12, "Standing forward bend", R.drawable.standing_forwordbend, false, false
        )

        exerciseList.add(standing_forwardBend)

        return exerciseList
    }
}