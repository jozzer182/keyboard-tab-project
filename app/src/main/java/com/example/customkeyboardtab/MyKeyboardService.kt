package com.example.customkeyboardtab

import android.inputmethodservice.InputMethodService
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button

class MyKeyboardService : InputMethodService() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateInputView(): View {
        val root = layoutInflater.inflate(R.layout.keyboard_view, null, false)

        val buttonMap = mapOf(
            R.id.btnTab1 to ::sendTab1,
            R.id.btnTab2 to ::sendTab2,
            R.id.btnTab3 to ::sendTab3,
            R.id.btnTab4 to ::sendTab4,
            R.id.btnTab5 to ::sendTab5,
            R.id.btnTab6 to ::sendTab6,
            R.id.btnTab7 to ::sendTab7,
            R.id.btnTab8 to ::sendTab8,
            R.id.btnTab9 to ::sendTab9,
            R.id.btnTab10 to ::sendTab10,
            R.id.btnTab11 to ::sendTab11,
            R.id.btnTab12 to ::sendTab12,
            R.id.btnTab13 to ::sendTab13,
            R.id.btnTab14 to ::sendTab14,
            R.id.btnTab15 to ::sendTab15,
            R.id.btnTab16 to ::sendTab16,
            R.id.btnTab17 to ::sendTab17,
            R.id.btnTab18 to ::sendTab18,
            R.id.btnTab19 to ::sendTab19,
            R.id.btnTab20 to ::sendTab20
        )

        for ((id, action) in buttonMap) {
            root.findViewById<Button>(id).setOnClickListener {
                Log.d("CustomKeyboardTab", "Presionado: $id")
                action()
            }
        }

        return root
    }

    private fun sendKeyEvent(keyCode: Int, metaState: Int = 0) {
        val now = System.currentTimeMillis()
        val ic = currentInputConnection ?: return

        val down = KeyEvent(
            now, now,
            KeyEvent.ACTION_DOWN, keyCode, 0, metaState,
            KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
            KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
        )

        val up = KeyEvent(
            now, now,
            KeyEvent.ACTION_UP, keyCode, 0, metaState,
            KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
            KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE
        )

        ic.sendKeyEvent(down)
        ic.sendKeyEvent(up)

        Log.d("CustomKeyboardTab", "Enviado TAB code=$keyCode meta=$metaState")
    }

    // Variantes Tab
    private fun sendTab1() = currentInputConnection?.commitText("\t", 1)  // Tab como texto
    private fun sendTab2() = sendKeyEvent(KeyEvent.KEYCODE_TAB)
    private fun sendTab3() {
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB))
    }
    private fun sendTab4() {
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_TAB))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_TAB))
    }
    private fun sendTab5() {
        handler.postDelayed({ sendKeyEvent(KeyEvent.KEYCODE_TAB) }, 100)
        handler.postDelayed({ sendKeyEvent(KeyEvent.KEYCODE_TAB) }, 200)
    }
    private fun sendTab6() {
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CTRL_LEFT))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_TAB))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_CTRL_LEFT))
    }
    private fun sendTab7() {
        val ic = currentInputConnection ?: return
        ic.commitText("  ", 1)
    }
    private fun sendTab8() {
        val now = System.currentTimeMillis()
        val tabDown = KeyEvent(
            now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB,
            0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
            KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE or KeyEvent.FLAG_FROM_SYSTEM
        )
        val tabUp = KeyEvent(
            now, now, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_TAB,
            0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
            KeyEvent.FLAG_SOFT_KEYBOARD or KeyEvent.FLAG_KEEP_TOUCH_MODE or KeyEvent.FLAG_FROM_SYSTEM
        )
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(tabDown)
        ic.sendKeyEvent(tabUp)
    }
    private fun sendTab9() = sendKeyEvent(KeyEvent.KEYCODE_TAB)
    private fun sendTab10() = sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_CTRL_ON)
    private fun sendTab11() {
        val ic = currentInputConnection ?: return
        ic.performContextMenuAction(android.R.id.paste)
    }
    private fun sendTab12() {
        currentInputConnection?.commitText("→", 1)
    }
    private fun sendTab13() {
        sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_ALT_ON)
    }
    private fun sendTab14() {
        sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_SHIFT_ON)
    }
    private fun sendTab15() {
        sendKeyEvent(KeyEvent.KEYCODE_FORWARD_DEL)
    }
    private fun sendTab16() {
        sendKeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT)
    }
    private fun sendTab17() {
        sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_SYM_ON)
    }
    private fun sendTab18() {
        sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_FUNCTION_ON)
    }
    private fun sendTab19() {
        sendKeyEvent(KeyEvent.KEYCODE_TAB, KeyEvent.META_META_ON)
    }
    private fun sendTab20() {
        val ic = currentInputConnection ?: return
        ic.commitText("[TAB]", 1)
    }
}
