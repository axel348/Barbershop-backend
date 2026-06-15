# Ejecutar todas las pruebas y generar reportes de cobertura

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot

Write-Host "=== product-service ===" -ForegroundColor Cyan
Push-Location "$root\product"
.\mvnw.cmd test -q
Pop-Location

Write-Host "=== user-service ===" -ForegroundColor Cyan
Push-Location "$root\user"
.\mvnw.cmd test -q
Pop-Location

Write-Host "=== barber-shop-bff ===" -ForegroundColor Cyan
Push-Location "$root\barber-shop-bff"
.\mvnw.cmd test -q
Pop-Location

Write-Host "=== barber-shop-frontend ===" -ForegroundColor Cyan
Push-Location "$root\barber-shop-frontend"
npm run test:coverage
Pop-Location

Write-Host ""
Write-Host "Reportes de cobertura:" -ForegroundColor Green
Write-Host "  product/target/site/jacoco/index.html"
Write-Host "  user/target/site/jacoco/index.html"
Write-Host "  barber-shop-bff/target/site/jacoco/index.html"
Write-Host "  barber-shop-frontend/coverage/index.html"
