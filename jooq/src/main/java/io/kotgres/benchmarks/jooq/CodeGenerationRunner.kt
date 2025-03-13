package io.kotgres.benchmarks.jooq

import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Property
import org.jooq.meta.jaxb.Target


fun main() {

    val cfg = org.jooq.meta.jaxb.Configuration()

        // Configure the database connection here
        .withJdbc(
            Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl("jdbc:postgresql://localhost:54330/kotgresbench")
                // You can also pass user/password and other JDBC properties in the optional properties tag:
                .withProperties(
                    Property()
                        .withKey("user")
                        .withValue("kotgresbench"),
                    Property()
                        .withKey("password")
                        .withValue("kotgresbench123")
                )
        )
        .withGenerator(
            Generator()
                .withDatabase(
                    Database()
                        .withName("org.jooq.meta.postgres.PostgresDatabase")
                        // All elements that are generated from your schema (A Java regular expression.
                        // Use the pipe to separate several expressions) Watch out for
                        // case-sensitivity. Depending on your database, this might be
                        // important!
                        //
                        // You can create case-insensitive regular expressions using this syntax: (?i:expr)
                        //
                        // Whitespace is ignored and comments are possible.
                        .withIncludes("users_with_id")

                    // The schema that is used locally as a source for meta information.
                    // This could be your development schema or the production schema, etc
                    // This cannot be combined with the schemata element.
                    //
                    // If left empty, jOOQ will generate all available schemata. See the
                    // manual's next section to learn how to generate several schemata
//                        .withInputSchema("[your database schema / owner / name]")
                )
                .withTarget(
                    Target()

                        // The destination package of your generated classes (within the
                        // destination directory)
                        //
                        // jOOQ may append the schema name to this package if generating multiple schemas,
                        // e.g. org.jooq.your.packagename.schema1
                        // org.jooq.your.packagename.schema2
                        .withPackageName("io.kotgres.benchmarks.jooq.gen")

                        // The destination directory of your generated classes
                        .withDirectory("./jooq/src/main/java/")
                )

        )
    GenerationTool().run(cfg)
}