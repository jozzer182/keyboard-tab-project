# Keyboard Tab Project

Este es un proyecto experimental de teclado personalizado para Android, diseñado con un propósito muy particular: lograr que la tecla **Tab** funcione como lo hace en un teclado físico, especialmente en entornos de desarrollo como **VSCode con GitHub Copilot** ejecutado desde **una tablet Android** usando **Codespaces** u otras plataformas de programación en la nube.

## 🎯 Objetivo

Recrear el comportamiento del tabulador (`Tab`) que permite aceptar sugerencias de autocompletado en GitHub Copilot desde un entorno Android, algo que actualmente **no funciona con teclados virtuales** tradicionales ni con el teclado en pantalla de Windows.

## 🛠️ Lo que se intentó

Se implementaron **más de 20 variantes** del comportamiento de la tecla Tab, con diferentes estrategias:

### ✅ Funcional en StackBlitz
- En StackBlitz (editor web), el tabulador del teclado personalizado sí funcionó para autocompletar etiquetas HTML (como `<p>`), lo que demuestra que algunos entornos interpretan correctamente el envío de `KEYCODE_TAB`.

### ❌ No funcional en VSCode con Codespaces
- En Codespaces ejecutado desde VSCode en Android, el comportamiento esperado no se logra. Al presionar la tecla Tab personalizada:
    - A veces el teclado se oculta automáticamente.
    - A veces el cursor salta a un componente gráfico (ej. la flecha `<` del diálogo de Copilot).
    - En ninguna variante se logró aceptar la sugerencia de Copilot como lo hace un teclado físico.

---

## 🚧 Variantes de Tab probadas

Aquí un resumen de las estrategias implementadas para simular la tecla `Tab`:

| Variante | Estrategia                                                             | Resultado                   |
|----------|------------------------------------------------------------------------|-----------------------------|
| Tab1     | `commitText("\t", 1)`                                                 | Solo inserta tab, no útil   |
| Tab2     | Dos eventos `ACTION_DOWN` consecutivos                                | No cambia comportamiento    |
| Tab3     | Solo eventos `ACTION_UP`                                              | Sin efecto                  |
| Tab4     | Tab con `handler.post()` para delay                                   | No mejora                   |
| Tab5     | Ctrl + Tab (simulado)                                                 | Cambia de pestaña en Chrome |
| Tab6     | Ctrl + Slash (`Ctrl+/`)                                               | A veces pega portapapeles   |
| Tab7     | Inserta 4 espacios                                                    | No activa autocompletado    |
| Tab8     | `KeyEvent` con todos los metadatos                                   | No efectivo                 |
| Tab9     | `KeyCharacterMap.VIRTUAL_KEYBOARD` con flags avanzados               | No logra comportamiento     |
| Tab10-16 | Variantes inspiradas en `getevent` del teclado físico                | No lograron emulación total |
| Tab17+   | Pruebas usando `Instrumentation` y otros métodos complejos (futuros) | En evaluación               |

---

## 📋 Pruebas con teclado físico

Gracias a `adb shell getevent -lt`, se capturaron los eventos reales generados por un teclado físico cuando se presiona `Tab`. El evento útil fue:

```
/dev/input/event13: EV_KEY KEY_TAB DOWN
/dev/input/event13: EV_KEY KEY_TAB UP
```

Este comportamiento sí activa el autocompletado de GitHub Copilot en Codespaces, por lo tanto, **la solución ideal sería poder emular exactamente ese evento del dispositivo `/dev/input/event13`**, algo que requiere acceso a bajo nivel del sistema Android, no disponible para teclados personalizados estándar.

---

## 📱 Limitaciones actuales

- Android **no permite emular eventos a nivel de `getevent`** sin permisos root o acceso al kernel.
- El `InputConnection` solo permite enviar eventos hasta cierto nivel, lo cual **no es suficiente para engañar a Codespaces/Copilot**.
- El comportamiento observado es específico de **VSCode Web ejecutado en tablet Android**, donde el teclado virtual no se trata igual que un teclado físico.

---

## 💡 Posibles caminos futuros

1. Crear un **servicio de accesibilidad** que inyecte eventos al sistema (a explorar).
2. Ejecutar un entorno con permisos root o usar una ROM modificada para probar emulación de bajo nivel.
3. Investigar extensiones de teclado con privilegios elevados (ej. teclados OEM).
4. Usar `adb shell sendevent` desde otro dispositivo como solución externa (experimental).

---

## 📂 Estructura del Proyecto

- `MyKeyboardService.kt`: lógica principal del teclado y sus variantes.
- `keyboard_view.xml`: layout con botones personalizados para cada Tab.
- `AndroidManifest.xml`: configuración para declarar el servicio de input method.
- `qwerty.xml`: versión antigua basada en `KeyboardView`, ahora reemplazada por `LinearLayout` con `Buttons`.

---

## 👨‍💻 Autor

Este proyecto fue desarrollado por [@jozzer182](https://github.com/jozzer182) como parte de una exploración técnica y personal para mejorar el trabajo desde dispositivos móviles y acercarse a nuevas formas de programar desde Android.

---

## 🙌 Contribuciones y feedback

Este proyecto está abierto a sugerencias, mejoras y contribuciones. Si encuentras una forma efectiva de emular el comportamiento de Tab como teclado físico en Android, ¡no dudes en abrir un issue o pull request!