import {useEffect, useState} from "react";
import redirectToLoginIfTokenInvalid from "../utils/redirectToLogin";
import {useHistory} from "react-router-dom";
import {Container, Paper} from "@material-ui/core";
import PostEditor from "../PostEditor";
import DogePost from "../DogePost";
import Timeline from "@material-ui/lab/Timeline";

interface HomeProps {
  doge: Doge
}

export default function Home ({ doge }: HomeProps) {
  const history = useHistory()

  useEffect(() => redirectToLoginIfTokenInvalid(history), [])
  useEffect(fetchDogePosts, [])
  const [dogePosts, setDogePosts] = useState<DogePost[]>([])
  const [dogePostEditingMode, setDogePostEditingMode] = useState<DogePostEditingMode>()

  return (
    <div style={{ marginTop: '2rem' }}>
      <Container maxWidth='sm'>
        <PostEditor reloadDogePosts={fetchDogePosts} editDogePost={setDogePostEditingMode} edit={dogePostEditingMode} />
        <Timeline align='left'>
          {dogePosts.map(dogePost => (
            <DogePost
              dogePost={dogePost}
              doge={doge}
              reloadDogePosts={fetchDogePosts}
              editDogePost={setDogePostEditingMode}
            />
          ))}
        </Timeline>
      </Container>
    </div>
  )

  function fetchDogePosts() {
    fetch("/api/v1/posting/get-all", {
      headers: {
        Authorization: localStorage.getItem('token') || ''
      }
    })
      .then(res => {
        if (res.ok)
          res.json().then((dogePosts: DogePost[]) => {
            setDogePosts(dogePosts)
          })
      })
  }
}
