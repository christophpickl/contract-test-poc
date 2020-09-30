rootProject.name = "contract-test-poc"

include(
        "test-lib",
        "service-consumer", "service-consumer:server-impl", "service-consumer:contract-dto",  // ... client, mock
        "service-provider", "service-provider:server-impl", "service-provider:contract-dto", "service-provider:contract-client", "service-provider:contract-mock"
)
