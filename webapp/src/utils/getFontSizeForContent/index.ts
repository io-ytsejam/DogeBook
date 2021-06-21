export function getFontSizeForContent(content: string) {
  const tenths = content.length / 100
  return `${tenths > 1 ? 1 : (2 - tenths)}rem`
}