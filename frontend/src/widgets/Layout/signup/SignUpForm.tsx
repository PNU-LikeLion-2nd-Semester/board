import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/shared/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/shared/ui/form";
import { Input } from "@/shared/ui/input";
import { formSchema } from "@/features/Auth/signup/schema";
import { useSignUp } from "@/shared/hooks/useSignUp";
import { useRouter } from "next/router";

export default function SignUpForm() {
  const { signup, isLoading, error } = useSignUp();
  const router = useRouter();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    await signup(values);
    router.push("/login");
    console.log(values);
  };

  return (
    <div className="flex flex-row justify-center box-border mt-12">
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Username</FormLabel>
                <FormControl>
                  <Input placeholder="아이디" {...field} disabled={isLoading} />
                </FormControl>
                <FormDescription>아이디를 입력하세요.</FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Password</FormLabel>
                <FormControl>
                  <Input
                    type="password"
                    placeholder="비밀번호"
                    {...field}
                    disabled={isLoading}
                  />
                </FormControl>
                <FormDescription>비밀번호를 입력하세요.</FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          {error && (
            <div className="p-3 mb-4 text-sm text-red-500 bg-red-50 rounded-md">
              {error}
            </div>
          )}
          <Button type="submit" disabled={isLoading}>
            {isLoading ? "회원가입 중..." : "회원가입"}
          </Button>
        </form>
      </Form>
    </div>
  );
}
