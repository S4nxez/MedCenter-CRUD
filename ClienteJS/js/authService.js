// AuthService - Gestión de JWT y refresh tokens

class AuthService {
  constructor() {
    this.tokenKey = "authToken";
    this.refreshTokenKey = "refreshToken";
  }

  saveTokens(token, refreshToken) {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.refreshTokenKey, refreshToken);
  }

  getToken() {
    return localStorage.getItem(this.tokenKey);
  }

  getRefreshToken() {
    return localStorage.getItem(this.refreshTokenKey);
  }

  clearTokens() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.refreshTokenKey);
  }

  hasTokens() {
    return this.getToken() !== null && this.getRefreshToken() !== null;
  }

  async refreshAccessToken() {
    const refreshToken = this.getRefreshToken();
    if (!refreshToken) {
      throw new Error("No refresh token available");
    }

    try {
      const response = await fetch(
        `http://localhost:8080/refresh-token?refreshToken=${encodeURIComponent(refreshToken)}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      if (response.status === 403) {
        // Token inválido o expirado
        this.clearTokens();
        window.location.href = "index.html";
        throw new Error("Session expired");
      }

      if (!response.ok) {
        throw new Error("Failed to refresh token");
      }

      const data = await response.json();
      this.saveTokens(data.token, data.refreshToken);
      return data.token;
    } catch (error) {
      console.error("Error refreshing token:", error);
      this.clearTokens();
      window.location.href = "index.html";
      throw error;
    }
  }

  async fetchWithToken(url, options = {}) {
    let token = this.getToken();

    if (!token) {
      throw new Error("No authentication token found");
    }

    if (!options.headers) {
      options.headers = {};
    }
    options.headers["Authorization"] = `Bearer ${token}`;

    let response = await fetch(url, options);

    if (response.status === 401) {
      try {
        token = await this.refreshAccessToken();
        options.headers["Authorization"] = `Bearer ${token}`;
        response = await fetch(url, options);
      } catch (error) {
        throw error;
      }
    }

    return response;
  }
}

const authService = new AuthService();
