import { z } from "zod";

export const formSchema = z.object({
  id: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
  password: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
});

export type LoginFormSchema = z.infer<typeof formSchema>;
