export interface User {
  id: string;
}

export interface AuthStore {
  user: User | null;
  setUser: (user: User) => void;
  logout: () => void;
}

export interface LoginRequest {
  id: string;
  password: string;
}

export interface LoginResponse {
  user: User;
  token: string;
}
