import {Box, Card, Container, TextField} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";
import {Alert} from "@material-ui/lab";
import React, {useState} from "react";

interface CommentEditorProps {
  dogePost: DogePost
  reloadDogePosts: () => void
}

export default function CommentEditor({ dogePost, reloadDogePosts }: CommentEditorProps) {
  const [content, setContent] = useState("Wow!")
  const [success, setSuccess] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')

  const { dogePostId } = dogePost

  return <Card>
    <Container maxWidth='sm'>
      <Typography>
        <h3>Add comment to doge post</h3>
        <form onSubmit={e => {
          e.preventDefault()
          postDogeComment()
        }}>
          <TextField
            required
            fullWidth={true}
            id="outlined-multiline-static"
            label='Add comment to doge post'
            multiline
            rows={2}
            variant="outlined"
            onChange={({ target }) => setContent(target.value)}
            value={content}
          />
          <Box display='flex' justifyContent='space-between'>
            <Button
              style={{ margin: '1.5rem 0' }}
              variant='contained'
              color='primary'
              type='submit'
            >
              add comment
            </Button>
          </Box>
        </form>
      </Typography>

      <Snackbar open={errorMessage !== ''}  autoHideDuration={6000} onClose={() => setErrorMessage('')}>
        { /* @ts-ignore */ }
        <Alert onClose={() => setErrorMessage(false)} severity="error">{errorMessage?.substr(0, 1000)}</Alert>
      </Snackbar>
      <Snackbar open={success}  autoHideDuration={6000} onClose={() => setSuccess(false)}>
        { /* @ts-ignore */ }
        <Alert onClose={() => setErrorMessage(false)} severity="success">Doge comment added successfully!</Alert>
      </Snackbar>
    </Container>
  </Card>

  function postDogeComment() {
    fetch("/api/v1/posting/add-comment", {
      method: 'PUT',
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        dogePostId,
        creationTime: (new Date()).toLocaleString(),
        content
      })
    }).then(res => {
      if (res.ok) {
        setSuccess(true)
        setContent("Wow!")
        reloadDogePosts()
      } else {
        res.text().then(setErrorMessage)
      }
    }).catch(({ message }) => setErrorMessage(message))
  }
}