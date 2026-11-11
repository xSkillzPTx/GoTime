# GoTime
Trabalho prático da unidade curricular de **Desenvolvimento de 
Aplicações Móveis**, da licenciatura em Engenharia Informática
na Universidade Politécnica de Tomar, no ano letivo 2026/27

Aplicação Android de agenda com **agendas partilhadas** 
integradas na agenda pessoal, 
e **avisos de saída inteligentes** baseados na localização 
(GPS).

### Como é que usamos o GPS?
A app calcula quando tens de sair para chegares a horas a 
cada evento.

## Autores

| Nº    | Nome         |
|-------|--------------|
| 26165 | Igor Fonseca |
| 27502 | Afonso Viana |

## Funcionalidades
- Autenticação de utilizadores
- Agenda pessoal para eventos únicos, recorrentes ou pessoais
- Agendas partilhadas com papéis (i.e. dono, editor, membro)
- Entrada em agendas partilhadas por código de convite ou QR code
- Vista unificada, isto é, eventos pessoais e partilhados na mesma agenda
- Avisos de saída calculados com a localização atual e o tempo de viagem
- Deteção de saída e chegada aos eventos (geofencing) e estatísticas de pontualidade

## Tecnologias

- Kotlin, Jetpack Compose, ViewModel
- Room (armazenamento local)
- Retrofit (comunicação com a API REST)
- Fused Location Provider e Geofencing API
- WorkManager / AlarmManager (notificações agendadas)
- OpenRouteService (tempos de viagem)

## Organização do trabalho

- `main`: versão estável
- `feature/<nome>`: uma branch por funcionalidade, integrada por pull request
