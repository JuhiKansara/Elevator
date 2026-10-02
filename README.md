# Elevator System (Java)

A from-scratch, object-oriented simulation of a multi-lift elevator system,
built to practice low-level design: modeling real-world entities and
reasoning carefully about state transitions and edge cases.

This milestone is the **console simulation** — a complete, runnable
elevator system with dispatch logic, tick-based movement, and a
pending-request queue. No framework, no UI: just the core design.

## What it does

- Models a `Building` containing multiple `Lift`s.
- Accepts two distinct kinds of requests:
  - **External requests** — a hallway button press (`floor + direction`, no
    destination known yet).
  - **Internal requests** — a lift-panel button press (`destination floor`,
    tied to a specific lift).
- A `Dispatcher` assigns each external request to the best available lift:
  prefers a lift already travelling toward the request in the right
  direction; otherwise picks the nearest idle lift; queues the request if
  no lift currently qualifies, and retries it once a lift's state changes.
- Each `Lift` tracks its own pending stops in two sorted sets (floors above
  it, floors below it), always serving the nearest stop in its current
  direction before reversing — so a mid-trip pickup is served without
  losing the lift's original destination.
- A tick-based simulation loop advances every lift one floor per tick,
  servicing any pending stop it passes along the way.

## Architecture

```
elevator/
 ├── model/        Direction, LiftMovement, Building, Lift
 ├── request/      ExternalRequest, InternalRequest
 ├── service/      Dispatcher
 └── Main.java     wiring + simulation loop
```

- **`model/`** — the system's core entities and their invariants (a
  `Lift` can't exist with an invalid floor, can't belong to two buildings,
  can't accept a destination outside its building's range).
- **`request/`** — the two distinct input events the system accepts, kept
  separate rather than one class with optional fields, since they carry
  genuinely different information.
- **`service/`** — cross-cutting coordination logic that spans multiple
  lifts, kept out of the entities themselves.

## Key design decisions

- **External vs. internal requests are separate classes**, not one
  `Request` class with a nullable destination — a hallway press and a
  lift-panel press carry different information and have different
  validation rules.
- **Invalid states are made unconstructable, not just checked.** A `Lift`
  cannot be assigned to two buildings: the `Building`↔`Lift` link is set
  by a single method (`Building.addLift`), not two independent steps that
  could disagree.
- **Per-lift stop tracking uses two `TreeSet<Integer>`s** (stops above,
  stops below current floor) rather than a FIFO queue, so a lift always
  serves the nearest stop in its current direction and only reverses once
  that direction is exhausted.
- **A lift's `movement` always reflects its true current state**, even
  immediately after arriving somewhere mid-tick — this was a real bug
  found during testing (state briefly lagged a tick behind reality,
  causing the dispatcher to miss a lift that had just gone idle).

## Running it

```bash
javac -d out elevator/Main.java elevator/model/*.java elevator/request/*.java elevator/service/*.java
java -cp out elevator.Main
```

`Main.java` wires up a sample building and lifts, fires a couple of
requests, and runs a fixed number of simulation ticks, printing each
lift's floor and movement state per tick.

## Tested scenarios

- A single lift completing an upward trip, then correctly reversing to
  serve a pending stop below its starting floor.
- Two lifts each correctly dispatched to the request nearest to them.
- A request with no currently-qualifying lift correctly queuing, then
  being picked up automatically once a busy lift goes idle.

## What's next

This milestone uses a straightforward, explicitly-called retry mechanism
(`Dispatcher.retryPending()`) to re-check queued requests after a lift's
state changes. A planned follow-up refactors this into an observer
pattern, where `Lift` notifies `Dispatcher` directly on state change —
closer to how a real, concurrent multi-lift controller would behave.

A REST API (Spring Boot) and a React frontend were built on top of this
same core logic in a later milestone, allowing buildings and lifts to be
created and simulated interactively through a browser.
