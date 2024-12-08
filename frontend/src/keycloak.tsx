// src/keycloak.js
import Keycloak from 'keycloak-js';

// Configuration object you get from your Keycloak setup
const keycloak = new Keycloak({
  url: 'http://localhost:8080',         // Base URL of Keycloak server
  realm: 'master',             // The realm you created
  clientId: 'swa_jodel'              // Your client ID
});

export default keycloak;