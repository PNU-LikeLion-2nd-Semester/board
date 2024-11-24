export interface User {
  username: string;
}

export interface AuthStore {
  user: User | null;
  setUser: (user: User) => void;
  logout: () => void;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  user: User;
  token: string;
}
