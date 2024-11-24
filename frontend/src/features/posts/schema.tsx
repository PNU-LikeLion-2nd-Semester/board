import { z } from "zod";

export const formSchema = z.object({
  title: z.string().min(2, {
    message: "Content must be at least 2 characters.",
  }),
  content: z.string().min(2, {
    message: "Content must be at least 2 characters.",
  }),
});

export type PostFormSchema = z.infer<typeof formSchema>;
