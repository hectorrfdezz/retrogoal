# Configuración de correo en RetroGoal

La aplicación incluye un servicio de correo basado en `spring-boot-starter-mail`.
Está desactivado por defecto para que el proyecto funcione sin credenciales.

## Activar emails

Define estas variables de entorno antes de arrancar la aplicación:

```powershell
$env:APP_MAIL_ENABLED="true"
$env:SMTP_HOST="smtp.gmail.com"
$env:SMTP_PORT="587"
$env:SMTP_USERNAME="tu_correo@gmail.com"
$env:SMTP_PASSWORD="tu_app_password"
$env:APP_MAIL_FROM="tu_correo@gmail.com"
```

Con Gmail debes usar una **contraseña de aplicación**, no la contraseña normal de la cuenta.

## Qué correos envía

- Al iniciar sesión: aviso de nuevo acceso a la cuenta.
- Al completar el pago con Stripe: confirmación del pedido pagado.

El servicio está diseñado para que si el SMTP falla, la tienda no se rompa: simplemente no se envía el email.
