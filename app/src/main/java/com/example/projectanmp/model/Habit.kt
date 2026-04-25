package com.example.projectanmp.model

class Habit(
    var id: Int,
    var name: String,
    var description: String,
    var status: String,
    var progress: Int,
    var unit: String,
    var limit: Int,
    var iconId: Int
) {
}