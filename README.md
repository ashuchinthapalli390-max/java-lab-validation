# Java lab validation

Java 17 solutions and actual execution evidence for authorized agency portal testing.

Run `python validate.py` from this directory. Dependencies are pinned and downloaded from Maven Central. Generated classes and downloaded jars are ignored. Logs and application scene snapshots are in `outputs/`.

| Lab | Sources |
|---|---|
| 2.1 | BinarySearch |
| 3.4 | ConstructorOverloading |
| 7.1 | ThreadsByExtending and ThreadsByRunnable |
| 7.2 | AliveJoinDemo |
| 7.3 | DaemonThreadDemo |
| 7.4 | ProducerConsumer |
| 8.1 | PackageExample imports com.example.Calculator |
| 8.2 | LabelImageApp loads an FXML Label and ImageView |
| 8.3 | TipCalculatorApp, controller and numeric model |
| 9.1–3 | JDBC SELECT, parameterized INSERT and DELETE |

Thread-message programs run indefinitely by default. Supply a positive repetition count to stop after a bounded demonstration. Timing remains 1, 2 and 3 seconds.

The label/image layout is declarative FXML and can be opened in Scene Builder. Java only launches the FXML scene. GUI `--snapshot` mode runs event checks and saves the app's own rendered scene.

For MySQL, prepare only a disposable lab database using `exercise09/schema.sql`, set `JDBC_URL`, `JDBC_USER`, `JDBC_PASSWORD`, and include Connector/J on the runtime classpath. Defaults point to localhost/mydatabase, root, and an empty password; no credentials are published. Local JDBC integration uses isolated H2 in MySQL compatibility mode. This does not establish live MySQL connectivity.

