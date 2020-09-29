rootProject.name = "contract-test-poc"

include("openapi-generator", "test-lib",
        "service-consumer", "service-consumer:server-impl",
        "service-provider", "service-provider:contract-client", "service-provider:contract-dto", "service-provider:contract-mock", "service-provider:server-impl")
