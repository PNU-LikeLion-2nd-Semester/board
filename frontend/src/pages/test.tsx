import Head from "next/head";
import {useState} from "react";
import {uploadPost} from "../shared/api/posts";

export default function testPage() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [image, setImage] = useState<File | null>(null);
  const [result, setResult] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setResult(null);
    setError(null);

    try {
      const response = await uploadPost({title, content}, image || undefined);
      setResult(JSON.stringify(response, null, 2));
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "An unknown error occurred"
      );
    }
  };

  return (
    <>
      <Head>
        <title>testPage</title>
      </Head>

      <div>
        <h1 className="text-2xl font-bold mb-4">Post Upload Test</h1>

        <div>
          <label htmlFor="title" className="block mb-1">
            Title:
          </label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            className="w-full p-2 border rounded"
          />
        </div>
        <div>
          <label htmlFor="content" className="block mb-1">
            Content:
          </label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            className="w-full p-2 border rounded"
          />
        </div>
        <div>
          <label htmlFor="image" className="block mb-1">
            Image (optional):
          </label>
          <input
            type="file"
            id="image"
            accept="image/*"
            onChange={(e) => setImage(e.target.files?.[0] || null)}
            className="w-full p-2 border rounded"
          />
        </div>
        <button
          type="submit"
          onClick={handleSubmit}
          className="w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Upload Post
        </button>
        {result && (
          <div className="mt-4 p-4 bg-green-100 border border-green-300 rounded">
            <h3 className="font-bold text-green-700">Success!</h3>
            <pre className="mt-2 whitespace-pre-wrap">{result}</pre>
          </div>
        )}
        {error && (
          <div className="mt-4 p-4 bg-red-100 border border-red-300 rounded">
            <h3 className="font-bold text-red-700">Error</h3>
            <p className="mt-2">{error}</p>
          </div>
        )}
      </div>
    </>
  );
}
