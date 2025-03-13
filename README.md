# Kotgres Benchmarks

Benchmarks for to compare the performance of the best known ORMs for Kotlin and Java.

The benchmarks have the goal to be as unbiased as possible and to follow the docs to implement the benchmark functions.

PRs are welcome to add more ORMs or improve the performance of the existing ones.

For Java it includes:
- Hibernate
- Ebean
- Jooq
- ORMLite

For Kotlin it includes:
- Exposed
- Ktorm
- Kotgres

## How to run

### Pre-requisites

Make sure you have Docker installed. If not, you can install it [here](https://docs.docker.com/desktop/).

### Running the benchmarks

Follow the next steps:
1. Open the file `java/io/kotgres/benchmarks/runner/BenchmarkRunner.kt`
2. Configure the main function to your liking. You can configure:
    - entityCount: how many entities are used to run the tests. The bigger the number the slower the benchmarks woill be
    - mode: you can choose to run all ORMs with `Mode.ALL`, or for specific languages using `Mode.KOTLIN` or `Mode.JAVA`
3. Right click on `BenchmarkRunner.kt` and select `Run 'BenchmarkRunnerKt'` (it can take up to 5 minutes with the default settings, be patient)
4. Wait for the benchmarks to finish and check the results in the console

## Results

The current top 3 looks like this:
🥇 Kotgres
🥈 ORMLite
🥉 Ebean

This is the output of that run on my local set up:
```
Kotgres (K): 0.53s (23.66x speed-up)
ORMLite (J): 0.57s (22.12x speed-up)
Ebean (J): 0.63s (19.94x speed-up)
JOOQ (J): 0.65s (19.31x speed-up)
Hibernate (J): 0.68s (18.55x speed-up)
Exposed (K): 6.73s (1.87x speed-up)
Ktorm (K): 12.59s (1.0x speed-up)
```
