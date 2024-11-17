import { create } from "zustand";
import { User, AuthStore } from "@/entities/login/types";

export const useAuthStore = create<AuthStore>((set) => ({
  user: null,
  setUser: (user) => set({ user }),
  logout: () => set({ user: null }),
}));
