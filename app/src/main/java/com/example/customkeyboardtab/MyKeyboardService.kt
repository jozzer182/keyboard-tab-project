package com.example.customkeyboardtab

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.InputConnection

class MyKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var kv: KeyboardView
    private lateinit var keyboard: Keyboard

    override fun onCreateInputView(): View {
        val root = layoutInflater.inflate(R.layout.keyboard_view, null, false)
        kv = root.findViewById(R.id.keyboard)
        keyboard = Keyboard(this, R.xml.qwerty)
        kv.keyboard = keyboard
        kv.setOnKeyboardActionListener(this)
        return root
    }



    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val ic: InputConnection = currentInputConnection ?: return

        when (primaryCode) {
            67 -> ic.deleteSurroundingText(1, 0) // Backspace
            9 -> ic.commitText("\t", 1)          // Tab
            32 -> ic.commitText(" ", 1)          // Space (por claridad)
            else -> {
                try {
                    ic.commitText(primaryCode.toChar().toString(), 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
}
