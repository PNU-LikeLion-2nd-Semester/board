export interface User {
  username: string;
}

export interface AuthStore {
  user: User | null;
  setUser: (user: User) => void;
  logout: () => void;
}

export interface SignUpRequest {
  username: string;
  password: string;
}

export interface SignUpResponse {
  user: User;
  token: string;
}
