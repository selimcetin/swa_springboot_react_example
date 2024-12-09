// src/main.tsx
import { createRoot } from "react-dom/client";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import App from "./App.tsx";
import keycloak from "./keycloak.tsx";

const onKeycloakEvent = (event: any, error: any) => {
  console.log("Keycloak event:", event, "Error:", error);
};

const onKeycloakTokens = (tokens: any) => {
  console.log("Keycloak tokens:", tokens);
};

createRoot(document.getElementById("root")!).render(
  <ReactKeycloakProvider
    authClient={keycloak}
    onEvent={onKeycloakEvent}
    onTokens={onKeycloakTokens}
    initOptions={{ onLoad: "login-required", pkceMethod: "S256" }}
  >
    <App />
  </ReactKeycloakProvider>
);
