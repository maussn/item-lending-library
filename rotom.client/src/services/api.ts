

export async function fetchHelloWorld() {
    const resp = await fetch("/api/hello")
    console.log(resp)
    const text = await resp.json()
    console.log(text)
    return text.message
}