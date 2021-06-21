import {Box, Card, Container, TextareaAutosize, TextField} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";
import {useEffect, useRef, useState} from "react";
import {Alert} from "@material-ui/lab";

interface PostEditorProps {
  reloadDogePosts: () => void
  edit: DogePostEditingMode|undefined
  editDogePost: (data: DogePostEditingMode|undefined) => void
}

export default function PostEditor({ reloadDogePosts, edit, editDogePost: setEditDogePost }: PostEditorProps) {
  const [errorMessage, setErrorMessage] = useState('')
  const [success, setSuccess] = useState(false)
  const [content, setContent] = useState('What on your mind?')
  const [newContent, setNewContent] = useState('')
  const { dogePostId, currentContent } = edit || {}
  const textField = useRef<HTMLDivElement>(null)

  useEffect(function () {
    setContent(currentContent || '')
    textField.current?.focus()
  }, [edit])

  return <Container maxWidth='sm'>
    <Card>
      <Container maxWidth='sm'>
        <Typography>
          {!edit ? <h2>Create new doge post</h2> : <h2>Edit doge post</h2>}
          <form onSubmit={e => {
            e.preventDefault()
            if (edit)
              editDogePost()
            else
              postDogePost()
          }}>
            <TextField
              ref={textField}
              required
              fullWidth={true}
              id="outlined-multiline-static"
              label={!edit ? 'New doge post' : 'Edit doge post'}
              multiline
              rows={4}
              defaultValue="What on your mind?"
              variant="outlined"
              onChange={({ target }) => setContent(target.value)}
              value={content}
            />
            <Box display='flex' justifyContent='space-between'>
              <Button
                style={{ margin: '1.5rem 0' }}
                variant='contained'
                color={!edit ? 'secondary' : 'primary'}
                type='submit'
              >
                {!edit ? 'add new post' : 'update post'}
              </Button>
              {edit ? <Button
                style={{ margin: '1.5rem 0' }}
                variant='text'
                onClick={() => setEditDogePost(undefined)}
              >
                cancel
              </Button> : null}
            </Box>
          </form>
        </Typography>

        <Snackbar open={errorMessage !== ''}  autoHideDuration={6000} onClose={() => setErrorMessage('')}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="error">{errorMessage?.substr(0, 1000)}</Alert>
        </Snackbar>
        <Snackbar open={success}  autoHideDuration={6000} onClose={() => setSuccess(false)}>
          { /* @ts-ignore */ }
          <Alert onClose={() => setErrorMessage(false)} severity="success">Doge post doge posted successfully!</Alert>
        </Snackbar>
      </Container>
    </Card>
  </Container>

  function postDogePost() {
    fetch("/api/v1/posting/create", {
      method: 'PUT',
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        content,
        creationTime: (new Date()).toLocaleString()
      })
    }).then(res => {
      if (res.ok) {
        setSuccess(true)
        reloadDogePosts()
      } else {
        res.text().then(setErrorMessage)
      }
    }).catch(({ message }) => setErrorMessage(message))
  }

  function editDogePost() {
    fetch("/api/v1/posting/edit-post", {
      method: 'PATCH',
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        content,
        dogePostId
      })
    }).then(res => {
      if (res.ok) {
        setSuccess(true)
        reloadDogePosts()
      } else {
        res.text().then(setErrorMessage)
      }
    }).catch(({ message }) => setErrorMessage(message)).finally(() => setEditDogePost(undefined))
  }
}