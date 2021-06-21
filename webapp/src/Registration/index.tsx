import Typography from "@material-ui/core/Typography";
import {Container, TextField} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import {useState} from "react";
import MuiAlert, { AlertProps } from '@material-ui/lab/Alert';
import Snackbar from "@material-ui/core/Snackbar/Snackbar";

export default function Registration () {
  const [firstname, setFirstname] = useState('')
  const [lastname, setLastname] = useState('')
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const [errorMessage, setErrorMessage] = useState('')
  const [success, setSuccess] = useState(false)

  return (
    <div>
      <Container maxWidth="xs">
        <Typography component="div" style={{ backgroundColor: '#cfe8fc' }} />
        <Typography>
          <h1>Create account</h1>
          <h2>There is only one step to become doge</h2>
        </Typography>
        <form
          onSubmit={event => {
            event.preventDefault()
            handleCreateAccount()
          }}
        >
          <TextField
            required
            onChange={({ target }) => setFirstname(target.value)}
            value={firstname}
            label="Firstname"
            fullWidth
            margin="normal"
            variant="outlined"
          />
          <TextField
            required
            onChange={({ target }) => setLastname(target.value)}
            value={lastname}
            label="Lastname"
            fullWidth
            margin="normal"
            variant="outlined"
          />
          <TextField
            onChange={({ target }) => setUsername(target.value)}
            value={username}
            label="Username"
            fullWidth
            margin="normal"
            variant="outlined"
          />
          <TextField
            required
            onChange={({ target }) => setPassword(target.value)}
            value={password}
            label="Password"
            type='password'
            fullWidth
            margin="normal"
            variant="outlined"
          />
          <Button
            type='submit'
            variant='contained'>Sign up</Button>
        </form>
        <Snackbar open={errorMessage !== ''}  autoHideDuration={6000} onClose={() => setErrorMessage('')}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="error">{errorMessage}</Alert>
        </Snackbar>
        <Snackbar open={success}  autoHideDuration={6000} onClose={() => setSuccess(false)}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="success">You're a doge! Now you can sign in</Alert>
        </Snackbar>
      </Container>
    </div>
  )

  function handleCreateAccount() {
    const body = JSON.stringify({
      firstname, lastname, username, password
    })

    fetch("/api/v1/authorization/sign-up/", {
      method: 'post', body, headers: {
        'Content-Type': 'application/json'
      }
    }).then(res => {
      if (res.ok) setSuccess(true)
      else res.text().then(setErrorMessage)
    }).catch(err => setErrorMessage(err))
  }
}

function Alert(props: AlertProps) {
  return <MuiAlert variant="filled" {...props} />;
}