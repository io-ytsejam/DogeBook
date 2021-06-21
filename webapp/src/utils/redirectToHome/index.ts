export default function redirectToHomeIfTokenValid (history: { push: (s: string) => void }) {
  const value = localStorage.getItem('token')?.split(' ')[1]

  if (!value) return

  fetch("/api/v1/authorization/is-token-valid", {
    method: 'post',
    body: JSON.stringify({ value }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(res => {
      if (res.ok) {
        res.text().then(msg => {
          if (msg === "true") history.push("/home")
        })
      }
    })
}