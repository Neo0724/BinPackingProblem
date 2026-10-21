# Bin Packing Problem

A Java implementation of the bin packing problem featuring **Best Fit** and **First Fit** strategies. Parcels are assigned to bins with a fixed maximum capacity while the program tracks each bin's remaining capacity.

## Algorithms

Both strategies process parcels in input order. If no existing bin can accommodate a parcel, the algorithm creates a new bin.

### Best Fit

For each parcel, Best Fit chooses a bin that can hold it and leaves the least remaining capacity after placement. It uses:

- **Red-Black Tree:** Nodes are keyed by bins' remaining capacities. The self-balancing tree supports efficient searches and updates as bin capacities change.
- **Priority queue per tree node:** Bins with the same remaining capacity are grouped together. The queue prioritizes the smallest bin ID, so it is selected first when bins tie on capacity.

### First Fit

For each parcel, First Fit scans the bins in array order and places the parcel in the first bin with enough remaining capacity. If none fits, it creates a new bin. Its bin collection is a custom array-backed list; unlike Best Fit, it does not use a Red-Black Tree.

If there are `n` bins, First Fit may inspect all `n` bins for each parcel, so a placement search takes `O(n)` time. Best Fit searches a tree of `b` distinct remaining-capacity keys in `O(log b)` time, with priority-queue insertion or removal taking `O(log k)` for `k` bins sharing a key.

## Shared features

- **Parcel and bin models:** Each bin tracks its remaining capacity and the parcels it contains.
- **Object-oriented design and generics:** The code models parcels, bins, and data structures as Java classes, using generics for numeric capacity types such as `Integer` and `Double`.
- **Custom collections:** The project includes array-backed and linked-list implementations used by the algorithms and models.

## Requirements

- Java Development Kit (JDK)

No external libraries or build tool are required.

## Build and run

Run these commands from the repository root in PowerShell:

```powershell
$sources = Get-ChildItem -Path .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d .\out $sources
java -cp .\out Main
```

The demo reads `resources\random_truck_integer.txt`, processes 100 parcels, and uses a maximum bin capacity of 600. Run it from the repository root so the relative input-file path resolves correctly.

`Main` runs the Best Fit demo and then the First Fit demo using the same parcel input.

## Project layout

```text
src/
  Algorithm/       Best Fit and First Fit algorithm code
  ArrayList/       Custom list implementations
  RedBlackTree/    Red-Black Tree implementation
  Tree/            Bin ID comparator for priority queues
  pojo/            Bin and Parcel models
  Main.java        Demo entry point
resources/         Sample parcel input files and data generator
```
