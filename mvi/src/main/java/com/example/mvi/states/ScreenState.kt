package com.example.mvi.states

interface ScreenState<V> {
    fun visit(screen: V)
}
