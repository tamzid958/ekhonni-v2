

export const inviteUser = (email: string) => {
  console.log("Opening Mailbox")
  const subject = encodeURIComponent("You're invited to Ekhonni!");
  const body = encodeURIComponent(`Hi,\n\nYou're invited to join Ekhonni.com! Click here to sign up:\n${window.location.origin}/auth/register?email=${email}\n\nBest,\nEkhonni Team`);

  window.location.href = `mailto:${email}?subject=${subject}&body=${body}`;
};
