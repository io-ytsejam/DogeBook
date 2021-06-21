import Typography from "@material-ui/core/Typography";
import {Container, TextField} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import React, {useEffect, useState} from "react";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";
import MuiAlert, { AlertProps } from '@material-ui/lab/Alert';
import {Link, useHistory} from 'react-router-dom';
import redirectToHomeIfTokenValid from "../utils/redirectToHome";

interface LoginProps {
  onLogin: () => void
}

export default function Login ({ onLogin }: LoginProps) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const [errorMessage, setErrorMessage] = useState('')
  const [success, setSuccess] = useState(false)
  const history = useHistory()
  const redirect = () => {
    redirectToHomeIfTokenValid(history)
    onLogin()
  }

  useEffect(redirect, [success])

  return (
    <div>
      <Container maxWidth="xs">
        <Typography component="div" style={{ backgroundColor: '#cfe8fc' }} />
        <Typography>
          <h1>Sign in</h1>
        </Typography>
        <form
          onSubmit={event => {
            event.preventDefault()
            handleLogin()
          }}
        >
          <TextField
            required
            onChange={({ target }) => setUsername(target.value)}
            value={username}
            label="User name"
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
            variant='contained'>Sign in</Button>
          <Link component={React.forwardRef((props, ref) => (
            <p style={{ textAlign: 'center' }}>
              <a style={{ textDecoration: 'none', color: 'inherit' }} {...props}>{props.children}</a>
            </p>
          ))} to='/registration'>
            Not a Doge yet? Create account
          </Link>
        </form>
        <Snackbar open={errorMessage !== ''}  autoHideDuration={2000} onClose={() => setErrorMessage('')}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="error">{errorMessage}</Alert>
        </Snackbar>
        <Snackbar open={success}  autoHideDuration={6000} onClose={() => setSuccess(false)}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="success">Welcome!</Alert>
        </Snackbar>
      </Container>
    </div>
  )

  function handleLogin() {
    const body = JSON.stringify({
      username, password
    })

    fetch("/api/v1/authorization/sign-in", {
      method: 'post', body, headers: {
        'Content-Type': 'application/json'
      }
    }).then(res => {
      if (res.ok) {
        setSuccess(true)
        res.text().then(token => localStorage.setItem("token", `Bearer ${token}`))
      }
      else res.text().then(errorMessage => {
        if (res.status === 401) setErrorMessage("Invalid username and/or password")
        else setErrorMessage(errorMessage)
      })
    }).catch(err => setErrorMessage(err))
  }
}

function Alert(props: AlertProps) {
  return <MuiAlert variant="filled" {...props} />;
}